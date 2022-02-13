import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IAddress, Address } from '../address.model';
import { AddressService } from '../service/address.service';
import { IPolicyUsers } from 'app/entities/policy-users/policy-users.model';
import { PolicyUsersService } from 'app/entities/policy-users/service/policy-users.service';
import { ICompany } from 'app/entities/company/company.model';
import { CompanyService } from 'app/entities/company/service/company.service';

@Component({
  selector: 'jhi-address-update',
  templateUrl: './address-update.component.html',
})
export class AddressUpdateComponent implements OnInit {
  isSaving = false;

  policyUsersSharedCollection: IPolicyUsers[] = [];
  companiesSharedCollection: ICompany[] = [];

  editForm = this.fb.group({
    id: [],
    area: [],
    landmark: [],
    taluka: [],
    district: [],
    state: [],
    pincode: [],
    lastModified: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
    policyUsers: [],
    company: [],
  });

  constructor(
    protected addressService: AddressService,
    protected policyUsersService: PolicyUsersService,
    protected companyService: CompanyService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ address }) => {
      this.updateForm(address);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const address = this.createFromForm();
    if (address.id !== undefined) {
      this.subscribeToSaveResponse(this.addressService.update(address));
    } else {
      this.subscribeToSaveResponse(this.addressService.create(address));
    }
  }

  trackPolicyUsersById(index: number, item: IPolicyUsers): number {
    return item.id!;
  }

  trackCompanyById(index: number, item: ICompany): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAddress>>): void {
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

  protected updateForm(address: IAddress): void {
    this.editForm.patchValue({
      id: address.id,
      area: address.area,
      landmark: address.landmark,
      taluka: address.taluka,
      district: address.district,
      state: address.state,
      pincode: address.pincode,
      lastModified: address.lastModified,
      lastModifiedBy: address.lastModifiedBy,
      policyUsers: address.policyUsers,
      company: address.company,
    });

    this.policyUsersSharedCollection = this.policyUsersService.addPolicyUsersToCollectionIfMissing(
      this.policyUsersSharedCollection,
      address.policyUsers
    );
    this.companiesSharedCollection = this.companyService.addCompanyToCollectionIfMissing(this.companiesSharedCollection, address.company);
  }

  protected loadRelationshipsOptions(): void {
    this.policyUsersService
      .query()
      .pipe(map((res: HttpResponse<IPolicyUsers[]>) => res.body ?? []))
      .pipe(
        map((policyUsers: IPolicyUsers[]) =>
          this.policyUsersService.addPolicyUsersToCollectionIfMissing(policyUsers, this.editForm.get('policyUsers')!.value)
        )
      )
      .subscribe((policyUsers: IPolicyUsers[]) => (this.policyUsersSharedCollection = policyUsers));

    this.companyService
      .query()
      .pipe(map((res: HttpResponse<ICompany[]>) => res.body ?? []))
      .pipe(
        map((companies: ICompany[]) => this.companyService.addCompanyToCollectionIfMissing(companies, this.editForm.get('company')!.value))
      )
      .subscribe((companies: ICompany[]) => (this.companiesSharedCollection = companies));
  }

  protected createFromForm(): IAddress {
    return {
      ...new Address(),
      id: this.editForm.get(['id'])!.value,
      area: this.editForm.get(['area'])!.value,
      landmark: this.editForm.get(['landmark'])!.value,
      taluka: this.editForm.get(['taluka'])!.value,
      district: this.editForm.get(['district'])!.value,
      state: this.editForm.get(['state'])!.value,
      pincode: this.editForm.get(['pincode'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      policyUsers: this.editForm.get(['policyUsers'])!.value,
      company: this.editForm.get(['company'])!.value,
    };
  }
}
