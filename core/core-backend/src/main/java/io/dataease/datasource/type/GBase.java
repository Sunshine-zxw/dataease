package io.dataease.datasource.type;

import io.dataease.datasource.security.JdbcUrlSecurityPolicy;
import io.dataease.extensions.datasource.vo.DatasourceConfiguration;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Data
@Component("GBase")
public class GBase extends DatasourceConfiguration {
    private String driver = "com.gbase.jdbc.Driver";
    private String extraParams = "characterEncoding=UTF-8&connectTimeout=5000";

    public String getJdbc() {
        String jdbcUrl = "";
        if (StringUtils.isNoneEmpty(getUrlType()) && !getUrlType().equalsIgnoreCase("hostName")) {
            jdbcUrl = getJdbcUrl();
        } else if (StringUtils.isEmpty(extraParams.trim())) {
            jdbcUrl = "jdbc:gbase://HOSTNAME:PORT/DATABASE"
                    .replace("HOSTNAME", getLHost().trim())
                    .replace("PORT", getLPort().toString().trim())
                    .replace("DATABASE", getDataBase().trim());
        } else {
            jdbcUrl = "jdbc:gbase://HOSTNAME:PORT/DATABASE?EXTRA_PARAMS"
                    .replace("HOSTNAME", getLHost().trim())
                    .replace("PORT", getLPort().toString().trim())
                    .replace("DATABASE", getDataBase().trim())
                    .replace("EXTRA_PARAMS", getExtraParams().trim());
        }
        return JdbcUrlSecurityPolicy.validate("gbase", getDriver(), jdbcUrl, getExtraParams());
    }
}
