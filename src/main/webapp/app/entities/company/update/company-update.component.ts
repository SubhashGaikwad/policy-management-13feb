import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ICompany, Company } from '../company.model';
import { CompanyService } from '../service/company.service';
import { ICompanyType } from 'app/entities/company-type/company-type.model';
import { CompanyTypeService } from 'app/entities/company-type/service/company-type.service';

@Component({
  selector: 'jhi-company-update',
  templateUrl: './company-update.component.html',
})
export class CompanyUpdateComponent implements OnInit {
  isSaving = false;

  companyTypesCollection: ICompanyType[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    address: [],
    branch: [],
    brnachCode: [],
    email: [null, []],
    imageUrl: [],
    contactNo: [],
    lastModified: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
    companyType: [],
  });

  constructor(
    protected companyService: CompanyService,
    protected companyTypeService: CompanyTypeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ company }) => {
      this.updateForm(company);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const company = this.createFromForm();
    if (company.id !== undefined) {
      this.subscribeToSaveResponse(this.companyService.update(company));
    } else {
      this.subscribeToSaveResponse(this.companyService.create(company));
    }
  }

  trackCompanyTypeById(index: number, item: ICompanyType): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompany>>): void {
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

  protected updateForm(company: ICompany): void {
    this.editForm.patchValue({
      id: company.id,
      name: company.name,
      address: company.address,
      branch: company.branch,
      brnachCode: company.brnachCode,
      email: company.email,
      imageUrl: company.imageUrl,
      contactNo: company.contactNo,
      lastModified: company.lastModified,
      lastModifiedBy: company.lastModifiedBy,
      companyType: company.companyType,
    });

    this.companyTypesCollection = this.companyTypeService.addCompanyTypeToCollectionIfMissing(
      this.companyTypesCollection,
      company.companyType
    );
  }

  protected loadRelationshipsOptions(): void {
    this.companyTypeService
      .query({ 'companyId.specified': 'false' })
      .pipe(map((res: HttpResponse<ICompanyType[]>) => res.body ?? []))
      .pipe(
        map((companyTypes: ICompanyType[]) =>
          this.companyTypeService.addCompanyTypeToCollectionIfMissing(companyTypes, this.editForm.get('companyType')!.value)
        )
      )
      .subscribe((companyTypes: ICompanyType[]) => (this.companyTypesCollection = companyTypes));
  }

  protected createFromForm(): ICompany {
    return {
      ...new Company(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      address: this.editForm.get(['address'])!.value,
      branch: this.editForm.get(['branch'])!.value,
      brnachCode: this.editForm.get(['brnachCode'])!.value,
      email: this.editForm.get(['email'])!.value,
      imageUrl: this.editForm.get(['imageUrl'])!.value,
      contactNo: this.editForm.get(['contactNo'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      companyType: this.editForm.get(['companyType'])!.value,
    };
  }
}
