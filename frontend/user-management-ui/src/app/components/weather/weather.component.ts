import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-weather',
  templateUrl: './weather.component.html',
  styleUrls: ['./weather.component.scss']
})
export class WeatherComponent {
  weatherForm: FormGroup;
  weatherData: any = null;
  loading = false;
  error: string | null = null;

  constructor(private fb: FormBuilder, private http: HttpClient) {
    this.weatherForm = this.fb.group({
      city: ['']
    });
  }

  getWeather() {
    const city = this.weatherForm.value.city;
    if (!city) return;
    this.loading = true;
    this.error = null;
    this.weatherData = null;
  // OpenWeatherMap API key
  const apiKey = 'ffd2f11a144196dd6703770aab793741';
    const url = `https://api.openweathermap.org/data/2.5/weather?q=${city}&appid=${apiKey}&units=metric`;
    this.http.get(url).subscribe({
      next: (data) => {
        this.weatherData = data;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'City not found or API error.';
        this.loading = false;
      }
    });
  }
}
