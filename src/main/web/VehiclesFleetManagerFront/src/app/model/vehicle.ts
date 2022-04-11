import {Vehicleinspection} from "./vehicleinspection";

export class Vehicle {
  id!:number;
  name!:number;
  vin!:number;
  registrationNumber!:string;
  carMileage:number|undefined;
  carLoadCapacity:number | undefined;
  lorrySemitrailer: boolean | undefined;
  numberOfSeats:number | undefined;
  engineCapacity:number| undefined;
  averageFuelConsumption:number | undefined;
  roadworthy: boolean | undefined;
  occupied: boolean | undefined;
  vehicleinspection: Vehicleinspection | undefined;
}
