import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IVehicleClass, VehicleClass } from '../vehicle-class.model';
import { VehicleClassService } from '../service/vehicle-class.service';

@Component({
  selector: 'jhi-vehicle-class-update',
  templateUrl: './vehicle-class-update.component.html',
})
export class VehicleClassUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    lastModified: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
  });

  constructor(protected vehicleClassService: VehicleClassService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vehicleClass }) => {
      this.updateForm(vehicleClass);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const vehicleClass = this.createFromForm();
    if (vehicleClass.id !== undefined) {
      this.subscribeToSaveResponse(this.vehicleClassService.update(vehicleClass));
    } else {
      this.subscribeToSaveResponse(this.vehicleClassService.create(vehicleClass));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVehicleClass>>): void {
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

  protected updateForm(vehicleClass: IVehicleClass): void {
    this.editForm.patchValue({
      id: vehicleClass.id,
      name: vehicleClass.name,
      lastModified: vehicleClass.lastModified,
      lastModifiedBy: vehicleClass.lastModifiedBy,
    });
  }

  protected createFromForm(): IVehicleClass {
    return {
      ...new VehicleClass(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
    };
  }
}
