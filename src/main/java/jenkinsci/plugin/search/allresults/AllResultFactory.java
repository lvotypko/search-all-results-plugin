/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jenkinsci.plugin.search.allresults;

import hudson.EnvVars;
import hudson.Extension;
import hudson.matrix.Axis;
import hudson.matrix.AxisList;
import hudson.matrix.MatrixRun;
import hudson.model.EnvironmentContributor;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.search.Search;
import hudson.search.SearchFactory;
import hudson.search.SearchableModelObject;
import java.io.IOException;
import java.util.Map;
import jenkinsci.plugin.search.SearchProperty;

/**
 * Contribute browser build environments
 * Set BROWSER_AXIS_PATH which contains path to browser, BROWSER_AXIS which contains name of version, and export path to the version into PATH
 * 
 * @author Lucie Votypkova
 */
@Extension
public class AllResultFactory extends SearchFactory {

    @Override
    public Search createFor(SearchableModelObject owner) {
        if(SearchProperty.showAllPossibleResults()){
            return new SearchAllResults();
        }
        return null;
    }

}
