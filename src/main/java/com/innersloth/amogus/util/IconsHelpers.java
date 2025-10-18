package com.innersloth.amogus.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import com.innersloth.amogus.AmongUs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.util.MacWindowUtil;
import net.minecraft.client.util.Window;
import net.minecraft.resource.InputSupplier;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

public class IconsHelpers {
    public static final String ICONS = "icons";
    public static final String STABLE = "";
    public static final String SNAPSHOT = "/snapshot";

    public static Window window() {
        return MinecraftClient.getInstance().getWindow();
    }

    private static InputSupplier<InputStream> getIcon(ResourcePack resourcePack,
                                                      Identifier identifier, String filename) throws IOException {
        InputSupplier<InputStream> supplier = resourcePack.open(ResourceType.CLIENT_RESOURCES,
                identifier.withSuffixedPath("/" + filename));

        if (supplier == null) {
            throw new IOException("could not find icon " + identifier.withSuffixedPath("/" + filename));
        }

        return supplier;
    }

    public static void setIcon(Identifier identifier, ResourcePack resourcePack) throws IOException {
        int i = GLFW.glfwGetPlatform();
        switch (i) {
            case GLFW.GLFW_PLATFORM_WIN32:
            case GLFW.GLFW_PLATFORM_X11:
                List<InputSupplier<InputStream>> list =
                        List.of(getIcon(resourcePack, identifier, "icon_16x16.png"),
                                getIcon(resourcePack, identifier, "icon_32x32.png"),
                                getIcon(resourcePack, identifier, "icon_48x48.png"),
                                getIcon(resourcePack, identifier, "icon_128x128.png"),
                                getIcon(resourcePack, identifier, "icon_256x256.png"));
                List<ByteBuffer> list2 = new ArrayList<>(list.size());

                try {
                    MemoryStack memoryStack = MemoryStack.stackPush();

                    try {
                        GLFWImage.Buffer buffer = GLFWImage.malloc(list.size(), memoryStack);

                        for (int j = 0; j < list.size(); ++j) {
                            NativeImage nativeImage = NativeImage.read(list.get(j).get());

                            try {
                                ByteBuffer byteBuffer =
                                        MemoryUtil.memAlloc(nativeImage.getWidth() * nativeImage.getHeight() * 4);
                                list2.add(byteBuffer);
                                byteBuffer.asIntBuffer().put(nativeImage.copyPixelsArgb());
                                buffer.position(j);
                                buffer.width(nativeImage.getWidth());
                                buffer.height(nativeImage.getHeight());
                                buffer.pixels(byteBuffer);
                            } catch (Throwable var20) {
                                if (nativeImage != null) {
                                    try {
                                        nativeImage.close();
                                    } catch (Throwable var19) {
                                        var20.addSuppressed(var19);
                                    }
                                }

                                throw var20;
                            }

                            if (nativeImage != null) {
                                nativeImage.close();
                            }
                        }

                        GLFW.glfwSetWindowIcon(window().getHandle(), (GLFWImage.Buffer) buffer.position(0));
                    } catch (Throwable var21) {
                        if (memoryStack != null) {
                            try {
                                memoryStack.close();
                            } catch (Throwable var18) {
                                var21.addSuppressed(var18);
                            }
                        }

                        throw var21;
                    }

                    if (memoryStack != null) {
                        memoryStack.close();
                    }
                    break;
                } finally {
                    list2.forEach(MemoryUtil::memFree);
                }
            case GLFW.GLFW_PLATFORM_COCOA:
                MacWindowUtil.setApplicationIconImage(getIcon(resourcePack, identifier, "minecraft.icns"));
            case GLFW.GLFW_PLATFORM_WAYLAND:
            case GLFW.GLFW_PLATFORM_NULL:
                break;
            default:
                AmongUs.LOGGER.warn("not setting icon for unrecognized platform {}", i);
        }
    }
}