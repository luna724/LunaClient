import { getSessionConfig } from "./module";
// DO NOT IMPORT ANYTHING!

export function getStatus() {
  return getSessionConfig()["status"]
} 

export function antiAntiMacroStatus() {
  return getSessionConfig()["antiAntiMacroStatus"]
}