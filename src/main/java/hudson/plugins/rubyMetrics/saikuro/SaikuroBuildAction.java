package hudson.plugins.rubyMetrics.saikuro;

import hudson.model.AbstractBuild;
import hudson.model.Action;
import hudson.model.Run;
import hudson.plugins.rubyMetrics.AbstractRubyMetricsBuildAction;
import hudson.plugins.rubyMetrics.saikuro.model.SaikuroFileDetail;
import hudson.plugins.rubyMetrics.saikuro.model.SaikuroFileResult;
import hudson.plugins.rubyMetrics.saikuro.model.SaikuroResult;
import hudson.util.ChartUtil;
import hudson.util.ChartUtil.NumberOnlyBuildLabel;
import hudson.util.DataSetBuilder;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import java.util.Collection;
import java.util.Collections;

public class SaikuroBuildAction extends AbstractRubyMetricsBuildAction{

    private SaikuroResult results;

    public SaikuroBuildAction(Run<?, ?> owner, SaikuroResult results) {
        super(owner);
        this.results = results;
    }

    public Collection<? extends Action> getProjectActions() {
      return Collections.singletonList(new SaikuroProjectAction(owner.getParent()));
    }

    @Override
    protected DataSetBuilder<String, NumberOnlyBuildLabel> getDataSetBuilder() {
        DataSetBuilder<String, ChartUtil.NumberOnlyBuildLabel> dsb = new DataSetBuilder<String, ChartUtil.NumberOnlyBuildLabel>();

        for (SaikuroBuildAction a = this; a != null; a = a.getPreviousResult()) {
            ChartUtil.NumberOnlyBuildLabel label = new ChartUtil.NumberOnlyBuildLabel(a.owner);

            dsb.add(a.getResults().getTotalComplexityAsInt(), "total complexity", label);
        }
        return dsb;
    }

    public Object getDynamic(final String link, final StaplerRequest request, final StaplerResponse response) {
        if (link.startsWith("file.")) {
            String file = link.substring(link.indexOf("file.") + 5);
            SaikuroFileResult fileResult = getResults().getFile(file);
            return new SaikuroFileDetail(owner, fileResult);
        }

        return null;
    }

    public String getDisplayName() {
        return "Saikuro report";
    }

    public String getUrlName() {
        return "saikuro";
    }

    public SaikuroResult getResults() {
        return results;
    }

}
