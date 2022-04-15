import {Component, OnInit} from '@angular/core';

declare var toggleTheme :any;
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'VehiclesFleetManagerFront';

  ngOnInit(): void {
    new toggleTheme();
  }

}
