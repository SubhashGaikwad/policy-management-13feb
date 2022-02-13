import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IVehicleDetails, VehicleDetails } from '../vehicle-details.model';
import { VehicleDetailsService } from '../service/vehicle-details.service';
import { Zone } from 'app/entities/enumerations/zone.model';

@Component({
  selector: 'jhi-vehicle-details-update',
  templateUrl: './vehicle-details-update.component.html',
})
export class VehicleDetailsUpdateComponent implements OnInit {
  isSaving = false;
  zoneValues = Object.keys(Zone);

  editForm = this.fb.group({
    id: [],
    name: [],
    invoiceValue: [],
    idv: [],
    enginNumber: [],
    chassisNumber: [],
    registrationNumber: [],
    seatingCapacity: [],
    zone: [],
    yearOfManufacturing: [],
    registrationDate: [],
    lastModified: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
  });

  constructor(
    protected vehicleDetailsService: VehicleDetailsService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vehicleDetails }) => {
      this.updateForm(vehicleDetails);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const vehicleDetails = this.createFromForm();
    if (vehicleDetails.id !== undefined) {
      this.subscribeToSaveResponse(this.vehicleDetailsService.update(vehicleDetails));
    } else {
      this.subscribeToSaveResponse(this.vehicleDetailsService.create(vehicleDetails));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVehicleDetails>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(vehicleDetails: IVehicleDetails): void {
    this.editForm.patchValue({
      id: vehicleDetails.id,
      name: vehicleDetails.name,
      invoiceValue: vehicleDetails.invoiceValue,
      idv: vehicleDetails.idv,
      enginNumber: vehicleDetails.enginNumber,
      chassisNumber: vehicleDetails.chassisNumber,
      registrationNumber: vehicleDetails.registrationNumber,
      seatingCapacity: vehicleDetails.seatingCapacity,
      zone: vehicleDetails.zone,
      yearOfManufacturing: vehicleDetails.yearOfManufacturing,
      registrationDate: vehicleDetails.registrationDate,
      lastModified: vehicleDetails.lastModified,
      lastModifiedBy: vehicleDetails.lastModifiedBy,
    });
  }

  protected createFromForm(): IVehicleDetails {
    return {
      ...new VehicleDetails(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      invoiceValue: this.editForm.get(['invoiceValue'])!.value,
      idv: this.editForm.get(['idv'])!.value,
      enginNumber: this.editForm.get(['enginNumber'])!.value,
      chassisNumber: this.editForm.get(['chassisNumber'])!.value,
      registrationNumber: this.editForm.get(['registrationNumber'])!.value,
      seatingCapacity: this.editForm.get(['seatingCapacity'])!.value,
      zone: this.editForm.get(['zone'])!.value,
      yearOfManufacturing: this.editForm.get(['yearOfManufacturing'])!.value,
      registrationDate: this.editForm.get(['registrationDate'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
    };
  }
}
