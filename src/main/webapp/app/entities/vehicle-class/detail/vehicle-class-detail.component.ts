import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVehicleClass } from '../vehicle-class.model';

@Component({
  selector: 'jhi-vehicle-class-detail',
  templateUrl: './vehicle-class-detail.component.html',
})
export class VehicleClassDetailComponent implements OnInit {
  vehicleClass: IVehicleClass | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vehicleClass }) => {
      this.vehicleClass = vehicleClass;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
