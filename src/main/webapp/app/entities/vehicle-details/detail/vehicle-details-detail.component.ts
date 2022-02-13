import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVehicleDetails } from '../vehicle-details.model';

@Component({
  selector: 'jhi-vehicle-details-detail',
  templateUrl: './vehicle-details-detail.component.html',
})
export class VehicleDetailsDetailComponent implements OnInit {
  vehicleDetails: IVehicleDetails | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vehicleDetails }) => {
      this.vehicleDetails = vehicleDetails;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
