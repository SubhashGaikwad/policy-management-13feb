import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IParameterLookup, ParameterLookup } from '../parameter-lookup.model';
import { ParameterLookupService } from '../service/parameter-lookup.service';
import { IVehicleDetails } from 'app/entities/vehicle-details/vehicle-details.model';
import { VehicleDetailsService } from 'app/entities/vehicle-details/service/vehicle-details.service';
import { ParameterType } from 'app/entities/enumerations/parameter-type.model';

@Component({
  selector: 'jhi-parameter-lookup-update',
  templateUrl: './parameter-lookup-update.component.html',
})
export class ParameterLookupUpdateComponent implements OnInit {
  isSaving = false;
  parameterTypeValues = Object.keys(ParameterType);

  vehicleDetailsSharedCollection: IVehicleDetails[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    type: [],
    lastModified: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
    vehicleDetails: [],
  });

  constructor(
    protected parameterLookupService: ParameterLookupService,
    protected vehicleDetailsService: VehicleDetailsService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ parameterLookup }) => {
      this.updateForm(parameterLookup);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const parameterLookup = this.createFromForm();
    if (parameterLookup.id !== undefined) {
      this.subscribeToSaveResponse(this.parameterLookupService.update(parameterLookup));
    } else {
      this.subscribeToSaveResponse(this.parameterLookupService.create(parameterLookup));
    }
  }

  trackVehicleDetailsById(index: number, item: IVehicleDetails): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IParameterLookup>>): void {
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

  protected updateForm(parameterLookup: IParameterLookup): void {
    this.editForm.patchValue({
      id: parameterLookup.id,
      name: parameterLookup.name,
      type: parameterLookup.type,
      lastModified: parameterLookup.lastModified,
      lastModifiedBy: parameterLookup.lastModifiedBy,
      vehicleDetails: parameterLookup.vehicleDetails,
    });

    this.vehicleDetailsSharedCollection = this.vehicleDetailsService.addVehicleDetailsToCollectionIfMissing(
      this.vehicleDetailsSharedCollection,
      parameterLookup.vehicleDetails
    );
  }

  protected loadRelationshipsOptions(): void {
    this.vehicleDetailsService
      .query()
      .pipe(map((res: HttpResponse<IVehicleDetails[]>) => res.body ?? []))
      .pipe(
        map((vehicleDetails: IVehicleDetails[]) =>
          this.vehicleDetailsService.addVehicleDetailsToCollectionIfMissing(vehicleDetails, this.editForm.get('vehicleDetails')!.value)
        )
      )
      .subscribe((vehicleDetails: IVehicleDetails[]) => (this.vehicleDetailsSharedCollection = vehicleDetails));
  }

  protected createFromForm(): IParameterLookup {
    return {
      ...new ParameterLookup(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      type: this.editForm.get(['type'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      vehicleDetails: this.editForm.get(['vehicleDetails'])!.value,
    };
  }
}
