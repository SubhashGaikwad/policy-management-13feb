import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IAgency, Agency } from '../agency.model';
import { AgencyService } from '../service/agency.service';

@Component({
  selector: 'jhi-agency-update',
  templateUrl: './agency-update.component.html',
})
export class AgencyUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    address: [],
    branch: [],
    brnachCode: [],
    email: [null, []],
    companyTypeId: [],
    imageUrl: [],
    contactNo: [],
    lastModified: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
  });

  constructor(protected agencyService: AgencyService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ agency }) => {
      this.updateForm(agency);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const agency = this.createFromForm();
    if (agency.id !== undefined) {
      this.subscribeToSaveResponse(this.agencyService.update(agency));
    } else {
      this.subscribeToSaveResponse(this.agencyService.create(agency));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAgency>>): void {
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

  protected updateForm(agency: IAgency): void {
    this.editForm.patchValue({
      id: agency.id,
      name: agency.name,
      address: agency.address,
      branch: agency.branch,
      brnachCode: agency.brnachCode,
      email: agency.email,
      companyTypeId: agency.companyTypeId,
      imageUrl: agency.imageUrl,
      contactNo: agency.contactNo,
      lastModified: agency.lastModified,
      lastModifiedBy: agency.lastModifiedBy,
    });
  }

  protected createFromForm(): IAgency {
    return {
      ...new Agency(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      address: this.editForm.get(['address'])!.value,
      branch: this.editForm.get(['branch'])!.value,
      brnachCode: this.editForm.get(['brnachCode'])!.value,
      email: this.editForm.get(['email'])!.value,
      companyTypeId: this.editForm.get(['companyTypeId'])!.value,
      imageUrl: this.editForm.get(['imageUrl'])!.value,
      contactNo: this.editForm.get(['contactNo'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
    };
  }
}
