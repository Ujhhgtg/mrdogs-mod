package dev.ujhhgtg.mrdogsmod.mixin.client;

import net.minecraft.util.crash.CrashReport;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(CrashReport.class)
public abstract class CrashReportClientMixin {
//    @Inject(method = "addDetails", at = @At("TAIL"))
//    private void addDetails(StringBuilder sb, CallbackInfo ci) {
//        if (CONFIG.removeCrashReport()) {
//            sb.delete(0, sb.length() - 1);
//            sb.append(I18n.translate("text.mrdogs-mod.crash_report_message"));
//        }
//    }
}
