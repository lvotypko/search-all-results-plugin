/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jenkinsci.plugin.search;

import hudson.Extension;
import hudson.model.User;
import hudson.model.UserProperty;
import hudson.model.UserPropertyDescriptor;
import java.util.Set;
import java.util.TreeSet;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.export.Exported;

/**
 *
 * @author lucinka
 */
public class SearchProperty extends hudson.model.UserProperty {

    private final boolean showAllPossibleResults; //show all possible result not only suggestion or founded item
    private final Set<String> filter; //Set of result types , which will be diesplayed

    public SearchProperty(boolean showAllPossibleResults, Set<String> filter) {
        this.showAllPossibleResults = showAllPossibleResults;
        this.filter = filter;
    }

    @Exported
    public boolean getShowAllPossibleResults() {
        return showAllPossibleResults;
    }

    public Set<String> getFilter() {
        return filter;
    }

    public static boolean hasFilter() {
        User user = User.current();
        if (user.getProperty(SearchProperty.class).getFilter() == null) {
            return false;
        }
        return true;
    }

    public static boolean showAllPossibleResults() {
        User user = User.current();
        //Searching for anonymous user shows all result only if there is not any instance of SearchableModelObject whith 
        //the same search name as the searched query
        boolean allSuggestedResults = false;
        if (user != null && user.getProperty(SearchProperty.class).getShowAllPossibleResults()) {
            allSuggestedResults = true;
        }
        return allSuggestedResults;
    }


    @Extension
    public static final class DescriptorImpl extends UserPropertyDescriptor {

        public String getDisplayName() {
            return "Search all result";
        }

        public UserProperty newInstance(User user) {
            //default setting is case-sensitive searching and not show all result if the searched query is the same 
            //as some search name of some instance of SearchModelObject class
            return new SearchProperty(false, null);
        }

        @Override
        public UserProperty newInstance(StaplerRequest req, JSONObject formData) throws FormException {
            boolean showAllPossibleResults = req.getParameter("showAllPossibleResults") != null;
            if (showAllPossibleResults) {
                Set<String> filter = new TreeSet<String>();
                String values[] = {"other", "job", "build", "view", "computer", "user"};
                for (String s : values) {
                    if (req.getParameter(s) != null) {
                        filter.add(s);
                    }
                }
                return new SearchProperty(showAllPossibleResults, filter);
            }
            return new SearchProperty( formData.optBoolean("showAllPossibleResults"), null);
        }
    }
}
