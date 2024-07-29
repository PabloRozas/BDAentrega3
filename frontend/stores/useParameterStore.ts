import { defineStore } from "pinia";

export interface ParameterStoreState {
  openHeader: boolean;
  openFooter: boolean;
  pathMenu: string;
  toggleMenuPath: string;
}

export const useParameterStore = defineStore({
  id: "parameterStore",
  state: (): ParameterStoreState => ({
    openHeader: false,
    openFooter: true,
    pathMenu: "M 0 100 L 0 0 L 100 0 L 100 100 Q 50 70 0 100  Z",
    toggleMenuPath:
      "M10 25L90 25zM10 50C50 50 50 50 90 50C50 50 50 50 10 50zM10 75L90 75z",
  }),
  actions: {
    toggleHeader() {
      this.openHeader = !this.openHeader;
    },
    toggleFooter() {
      this.openFooter = !this.openFooter;
    },
    setPathMenu(path: string) {
      this.pathMenu = path;
    },
  },
});
