package me.gavin.blm.asm;

import me.gavin.blm.asm.patches.*;

import java.util.ArrayList;

public final class PatchManager {

    public static boolean deobfuscated;
    private final ArrayList<ClassPatch> patches = new ArrayList<>();

    public static PatchManager INSTANCE = new PatchManager();

    public PatchManager() {
        // add patches here
        patches.add(new NetworkManagerPatch());
        patches.add(new EntityRendererPatch());
        patches.add(new EntityPlayerSPPatch());
        patches.add(new BlockPortalPatch());
        patches.add(new GuiNewChatPatch());
        patches.add(new RenderPlayerPatch());
        patches.add(new RenderLivingBasePatch());
        patches.add(new EntityPlayerPatch());
    }

    public ClassPatch getPatch(String className) {
        for (ClassPatch patch : patches) {

            String name = patch.getObfName();

            if (deobfuscated)
                name = patch.getName();

            if (name.equals(className))
                return patch;
        }

        return null;
    }
}
