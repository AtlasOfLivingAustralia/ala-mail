package au.org.ala.mail;

import com.amazonaws.regions.Regions;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "mail.ses")
class AlaAwsSesConfigurationProperties {

    private String configSet;

    private Regions region;

    public String getConfigSet() {
        return configSet;
    }

    public void setConfigSet(String configSet) {
        this.configSet = configSet;
    }

    public Regions getRegion() {
        return region;
    }

    public void setRegion(Regions region) {
        this.region = region;
    }
}
