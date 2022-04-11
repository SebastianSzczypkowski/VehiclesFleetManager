import {Driver} from "./driver";

export class Cargo {
  id!: number;
  name!: string;
  description: string | undefined;
  type: string | undefined;
  sensitivity:string | undefined;
  specialRemakrs:string | undefined;
  delivered!:boolean;
  assigned!:boolean;
  weight:number|undefined;
  width:number | undefined;
  height:number | undefined;
  depth:number | undefined;
  driver:Driver | undefined;
  //TODO obsługa dat
}
