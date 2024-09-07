import { AutoGardenUnregisterEvent } from "./Ordermade/AutoGarden";

register("command", (arg) => {
  if (arg.length < 1 || arg.length > 1) return;
  let termEve = arg[0];

  switch (termEve) {
    case "autogaden":
      AutoGardenUnregisterEvent();
      break;
  }


}).setName("lunaclient_terminate_event")