package br.ufal.ic.academic;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import lombok.Getter;
import lombok.NonNull;

import javax.validation.Valid;

@Getter
public class AplicationConfig extends Configuration {
    @NonNull
    @Valid
    @JsonProperty("database")
    private DataSourceFactory database = new DataSourceFactory();

    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

}
