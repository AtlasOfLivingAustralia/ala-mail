package au.org.ala.mail;

import org.springframework.boot.context.properties.ConfigurationProperties;
import software.amazon.awssdk.regions.Region;

@ConfigurationProperties(prefix = "mail.ses")
class AlaAwsSesConfigurationProperties {

    private String configSet;

    private Region region;

    public String getConfigSet() {
        return configSet;
    }

    public void setConfigSet(String configSet) {
        this.configSet = configSet;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }
}
