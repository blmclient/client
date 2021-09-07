package me.gavin.blm.asm;

import java.util.ArrayList;

public class PatchManager {

    private final ArrayList<ClassPatch> patches = new ArrayList<>();

    public PatchManager() {
        // add patches here
    }

    public ClassPatch getPatch(String className) {
        for (ClassPatch patch : patches) {
            if (patch.getName().equals(className))
                return patch;
        }

        return null;
    }
}
