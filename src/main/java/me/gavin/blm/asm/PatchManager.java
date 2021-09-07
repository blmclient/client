package me.gavin.blm.asm;

import me.gavin.blm.asm.patches.EntityRendererPatch;
import me.gavin.blm.asm.patches.NetworkManagerPatch;

import java.util.ArrayList;

public class PatchManager {

    public static boolean deobfuscated;
    private final ArrayList<ClassPatch> patches = new ArrayList<>();

    public PatchManager() {
        // add patches here
        patches.add(new NetworkManagerPatch());
        patches.add(new EntityRendererPatch());
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
