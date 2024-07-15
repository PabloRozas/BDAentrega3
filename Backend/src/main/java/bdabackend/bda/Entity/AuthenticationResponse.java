package bdabackend.bda.Entity;

public class AuthenticationResponse {
    private String token;
    private String role;

    // Constructor con un solo parametro
    public AuthenticationResponse(String token, String role) {
        this.token = token;
        this.role = role;
    }

    // Constructor sin parametros
    public AuthenticationResponse() {
    }

    // Getters y Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public static class Builder {
        private String token;
        private String role;

        public Builder() {
        }

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public Builder role(String role) {
            this.role = role;
            return this;
        }

        public AuthenticationResponse build() {
            return new AuthenticationResponse(token, role);
        }
    }

}
