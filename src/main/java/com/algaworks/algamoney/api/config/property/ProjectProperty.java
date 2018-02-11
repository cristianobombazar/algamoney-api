package com.algaworks.algamoney.api.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "algamoney")
public class ProjectProperty {

    private final Security security = new Security();
    private String originPermitida  = "http://localhost:8000";

    public String getOriginPermitida() {
        return originPermitida;
    }

    public void setOriginPermitida(String originPermitida) {
        this.originPermitida = originPermitida;
    }

    public class Security{
        private boolean enableHttps;

        public boolean isEnableHttps() {
            return enableHttps;
        }
        public void setEnableHttps(boolean enableHttps) {
            this.enableHttps = enableHttps;
        }
    }

    public Security getSecurity() {
        return security;
    }
}
