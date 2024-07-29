import { defineStore } from "pinia";
import { RoleTypes } from "~/models/auth/role_types.model";

// todo: add _id to the state
export interface AuthStoreState {
  isAuthenticated: boolean;
  role: RoleTypes;
  token: string;
}

export const useAuthStore = defineStore({
  id: "authStore",
  state: (): AuthStoreState => ({
    isAuthenticated: false,
    role: RoleTypes.VISITANTE,
    token: "",
  }),
  actions: {
    login(token: string, role: RoleTypes) {
      this.isAuthenticated = true;
      this.role = role;
      this.token = token;
    },
    logout() {
      this.isAuthenticated = false;
      this.role = RoleTypes.VISITANTE;
      this.token = "";
    },
    getToken() {
      return this.token;
    },
    getRole() {
      return this.role;
    },
    setRole(role: RoleTypes) {
      this.role = role;
    },
  },
});
