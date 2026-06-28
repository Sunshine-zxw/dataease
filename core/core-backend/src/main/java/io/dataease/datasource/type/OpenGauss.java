package io.dataease.datasource.type;

import io.dataease.datasource.security.JdbcUrlSecurityPolicy;
import io.dataease.extensions.datasource.vo.DatasourceConfiguration;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Data
@Component("openGauss")
public class OpenGauss extends DatasourceConfiguration {
    private String driver = "org.opengauss.Driver";
    private String extraParams = "sslmode=disable";

    public String getJdbc() {
        String jdbcUrl = "";
        if (StringUtils.isNoneEmpty(getUrlType()) && !getUrlType().equalsIgnoreCase("hostName")) {
            jdbcUrl = getJdbcUrl();
        } else if (StringUtils.isEmpty(extraParams.trim())) {
            if (StringUtils.isEmpty(getSchema())) {
                jdbcUrl = "jdbc:opengauss://HOSTNAME:PORT/DATABASE"
                        .replace("HOSTNAME", getLHost().trim())
                        .replace("PORT", getLPort().toString().trim())
                        .replace("DATABASE", getDataBase().trim());
            } else {
                jdbcUrl = "jdbc:opengauss://HOSTNAME:PORT/DATABASE?currentSchema=SCHEMA"
                        .replace("HOSTNAME", getLHost().trim())
                        .replace("PORT", getLPort().toString().trim())
                        .replace("DATABASE", getDataBase().trim())
                        .replace("SCHEMA", getSchema().trim());
            }
        } else {
            jdbcUrl = "jdbc:opengauss://HOSTNAME:PORT/DATABASE?EXTRA_PARAMS"
                    .replace("HOSTNAME", getLHost().trim())
                    .replace("PORT", getLPort().toString().trim())
                    .replace("DATABASE", getDataBase().trim())
                    .replace("EXTRA_PARAMS", getExtraParams().trim());
        }
        return JdbcUrlSecurityPolicy.validate("opengauss", getDriver(), jdbcUrl, getExtraParams());
    }

    private static final Pattern DB_NAME_PATTERN = Pattern.compile("//[^/]+/([^?]+)");

    @Override
    protected Pattern getDatabasePattern() {
        return DB_NAME_PATTERN;
    }
}
