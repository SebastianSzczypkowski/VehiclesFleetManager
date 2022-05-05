import {Driver} from "./driver";
import {Coordinates} from "./coordinates";
import {Cargo} from "./cargo";

export class Road {

  id!:number;
  driver!:Driver;
  end!:Coordinates;
  finished!:boolean;
  start!:Coordinates;
  cargo!:Cargo;

}
