import { Component } from '@angular/core';
import { WeatherService } from '../../services/weather.service';

@Component({
  selector: 'app-weather',
  templateUrl: './weather.component.html',
  styleUrls: ['./weather.component.scss']
})
export class WeatherComponent {
  city = '';
  weather: any;
  error: string | null = null;

  constructor(private weatherService: WeatherService) {}

  getWeather() {
    this.weatherService.getWeather(this.city).subscribe({
      next: (data) => {
        this.weather = data;
        this.error = null;
      },
      error: () => {
        this.weather = null;
        this.error = 'City not found!';
      }
    });
  }
}
