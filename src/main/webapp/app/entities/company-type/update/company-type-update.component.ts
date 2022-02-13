import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ICompanyType, CompanyType } from '../company-type.model';
import { CompanyTypeService } from '../service/company-type.service';

@Component({
  selector: 'jhi-company-type-update',
  templateUrl: './company-type-update.component.html',
})
export class CompanyTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    lastModified: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
  });

  constructor(protected companyTypeService: CompanyTypeService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ companyType }) => {
      this.updateForm(companyType);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const companyType = this.createFromForm();
    if (companyType.id !== undefined) {
      this.subscribeToSaveResponse(this.companyTypeService.update(companyType));
    } else {
      this.subscribeToSaveResponse(this.companyTypeService.create(companyType));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompanyType>>): void {
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

  protected updateForm(companyType: ICompanyType): void {
    this.editForm.patchValue({
      id: companyType.id,
      name: companyType.name,
      lastModified: companyType.lastModified,
      lastModifiedBy: companyType.lastModifiedBy,
    });
  }

  protected createFromForm(): ICompanyType {
    return {
      ...new CompanyType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
    };
  }
}
