export class Coordinates {
  constructor(_lat: number | undefined, lon: number | undefined) {
    this._lat=_lat;
    this._lon=lon;

  }


  _lon: number | undefined;
   _lat: number | undefined;
  details: string | undefined;
  color: string | undefined;
  name: string | undefined;

  get lon(): number | undefined {
    return this._lon;
  }

  set lon(value: number | undefined) {
    this._lon = value;
  }

  get lat(): number | undefined {
    return this._lat;
  }

  set lat(value: number | undefined) {
    this._lat = value;
  }
}
