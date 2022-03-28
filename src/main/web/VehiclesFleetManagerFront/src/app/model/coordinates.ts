export class Coordinates {

  _lon: number | undefined;
   _lat: number | undefined;

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
