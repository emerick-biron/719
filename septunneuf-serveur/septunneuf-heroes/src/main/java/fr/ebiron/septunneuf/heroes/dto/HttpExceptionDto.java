package fr.ebiron.septunneuf.heroes.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class HttpExceptionDto {
    private int statusCode;
    private String reasonPhrase;
    private String details;
    private Object additionalData;

    public HttpExceptionDto(int statusCode, String reasonPhrase, String details, Object additionalData) {
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
        this.details = details;
        this.additionalData = additionalData;
    }

    public HttpExceptionDto(int statusCode, String reasonPhrase, String details) {
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
        this.details = details;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    public void setReasonPhrase(String reasonPhrase) {
        this.reasonPhrase = reasonPhrase;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Object getAdditionalData() {
        return additionalData;
    }

    public void setAdditionalData(Object additionalData) {
        this.additionalData = additionalData;
    }
}
