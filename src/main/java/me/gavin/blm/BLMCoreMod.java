package me.gavin.blm;

import me.gavin.blm.asm.ASMClassTransformer;
import me.gavin.blm.asm.PatchManager;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

@IFMLLoadingPlugin.Name("BLM ASM")
@IFMLLoadingPlugin.MCVersion("1.10.2")
public final class BLMCoreMod implements IFMLLoadingPlugin {
    @Override
    public String[] getASMTransformerClass() {
        return new String[] {
                ASMClassTransformer.class.getName()
        };
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
        final boolean isObfuscated = (boolean) data.getOrDefault("runtimeDeobfuscationEnabled", true);
        PatchManager.deobfuscated = !isObfuscated;
        System.out.println(PatchManager.deobfuscated);
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
