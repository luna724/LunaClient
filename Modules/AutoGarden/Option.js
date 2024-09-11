import { getConfig } from "./XYZ/module";
// DO NOT IMPORT ANYTHING!

export function getStatus() {
  //return getSessionConfig()["status"]
  return false;
} 

const temporaryConfig = null;
export function setTemporaryConfig(newcfg) {
  temporaryConfig = newcfg;
}

export function getTemporaryConfig() {
  return getConfig();
}