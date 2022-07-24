import {Userrole} from "./userrole";

export class User {
  id!:number;
  name!:string;
  login!:string|undefined;
  active!:boolean|undefined;
  dateOfRegistration!:string|undefined;
  email!:string|undefined;
  password!:string|undefined;
  roles:Userrole[]=[];

}
