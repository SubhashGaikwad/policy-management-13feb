import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IPolicy, Policy } from '../policy.model';
import { PolicyService } from '../service/policy.service';
import { IAgency } from 'app/entities/agency/agency.model';
import { AgencyService } from 'app/entities/agency/service/agency.service';
import { ICompany } from 'app/entities/company/company.model';
import { CompanyService } from 'app/entities/company/service/company.service';
import { IProduct } from 'app/entities/product/product.model';
import { ProductService } from 'app/entities/product/service/product.service';
import { IPremiunDetails } from 'app/entities/premiun-details/premiun-details.model';
import { PremiunDetailsService } from 'app/entities/premiun-details/service/premiun-details.service';
import { IVehicleClass } from 'app/entities/vehicle-class/vehicle-class.model';
import { VehicleClassService } from 'app/entities/vehicle-class/service/vehicle-class.service';
import { IBankDetails } from 'app/entities/bank-details/bank-details.model';
import { BankDetailsService } from 'app/entities/bank-details/service/bank-details.service';
import { IPolicyUsers } from 'app/entities/policy-users/policy-users.model';
import { PolicyUsersService } from 'app/entities/policy-users/service/policy-users.service';
import { PremiumMode } from 'app/entities/enumerations/premium-mode.model';
import { PolicyStatus } from 'app/entities/enumerations/policy-status.model';
import { Zone } from 'app/entities/enumerations/zone.model';
import { PolicyType } from 'app/entities/enumerations/policy-type.model';

@Component({
  selector: 'jhi-policy-update',
  templateUrl: './policy-update.component.html',
})
export class PolicyUpdateComponent implements OnInit {
  isSaving = false;
  premiumModeValues = Object.keys(PremiumMode);
  policyStatusValues = Object.keys(PolicyStatus);
  zoneValues = Object.keys(Zone);
  policyTypeValues = Object.keys(PolicyType);

  agenciesCollection: IAgency[] = [];
  companiesCollection: ICompany[] = [];
  productsCollection: IProduct[] = [];
  premiunDetailsCollection: IPremiunDetails[] = [];
  vehicleClassesCollection: IVehicleClass[] = [];
  bankDetailsCollection: IBankDetails[] = [];
  policyUsersSharedCollection: IPolicyUsers[] = [];

  editForm = this.fb.group({
    id: [],
    policyAmount: [],
    policyNumber: [],
    term: [],
    ppt: [],
    commDate: [null, [Validators.required]],
    proposerName: [],
    sumAssuredAmount: [],
    premiumMode: [],
    basicPremium: [],
    extraPremium: [],
    gst: [],
    status: [],
    totalPremiun: [],
    gstFirstYear: [],
    netPremium: [],
    taxBeneficiary: [],
    policyReceived: [],
    previousPolicy: [],
    policyStartDate: [],
    policyEndDate: [],
    period: [],
    claimDone: [],
    freeHeathCheckup: [],
    zone: [],
    noOfYear: [],
    floaterSum: [],
    tpa: [],
    paymentDate: [],
    policyType: [],
    paToOwner: [],
    paToOther: [],
    loading: [],
    riskCoveredFrom: [],
    riskCoveredTo: [],
    notes: [],
    freeField1: [],
    freeField2: [],
    freeField3: [],
    freeField4: [],
    freeField5: [],
    maturityDate: [null, [Validators.required]],
    uinNo: [],
    lastModified: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
    agency: [],
    company: [],
    product: [],
    premiunDetails: [],
    vehicleClass: [],
    bankDetails: [],
    policyUsers: [],
  });

  constructor(
    protected policyService: PolicyService,
    protected agencyService: AgencyService,
    protected companyService: CompanyService,
    protected productService: ProductService,
    protected premiunDetailsService: PremiunDetailsService,
    protected vehicleClassService: VehicleClassService,
    protected bankDetailsService: BankDetailsService,
    protected policyUsersService: PolicyUsersService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ policy }) => {
      this.updateForm(policy);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const policy = this.createFromForm();
    if (policy.id !== undefined) {
      this.subscribeToSaveResponse(this.policyService.update(policy));
    } else {
      this.subscribeToSaveResponse(this.policyService.create(policy));
    }
  }

  trackAgencyById(index: number, item: IAgency): number {
    return item.id!;
  }

  trackCompanyById(index: number, item: ICompany): number {
    return item.id!;
  }

  trackProductById(index: number, item: IProduct): number {
    return item.id!;
  }

  trackPremiunDetailsById(index: number, item: IPremiunDetails): number {
    return item.id!;
  }

  trackVehicleClassById(index: number, item: IVehicleClass): number {
    return item.id!;
  }

  trackBankDetailsById(index: number, item: IBankDetails): number {
    return item.id!;
  }

  trackPolicyUsersById(index: number, item: IPolicyUsers): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPolicy>>): void {
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

  protected updateForm(policy: IPolicy): void {
    this.editForm.patchValue({
      id: policy.id,
      policyAmount: policy.policyAmount,
      policyNumber: policy.policyNumber,
      term: policy.term,
      ppt: policy.ppt,
      commDate: policy.commDate,
      proposerName: policy.proposerName,
      sumAssuredAmount: policy.sumAssuredAmount,
      premiumMode: policy.premiumMode,
      basicPremium: policy.basicPremium,
      extraPremium: policy.extraPremium,
      gst: policy.gst,
      status: policy.status,
      totalPremiun: policy.totalPremiun,
      gstFirstYear: policy.gstFirstYear,
      netPremium: policy.netPremium,
      taxBeneficiary: policy.taxBeneficiary,
      policyReceived: policy.policyReceived,
      previousPolicy: policy.previousPolicy,
      policyStartDate: policy.policyStartDate,
      policyEndDate: policy.policyEndDate,
      period: policy.period,
      claimDone: policy.claimDone,
      freeHeathCheckup: policy.freeHeathCheckup,
      zone: policy.zone,
      noOfYear: policy.noOfYear,
      floaterSum: policy.floaterSum,
      tpa: policy.tpa,
      paymentDate: policy.paymentDate,
      policyType: policy.policyType,
      paToOwner: policy.paToOwner,
      paToOther: policy.paToOther,
      loading: policy.loading,
      riskCoveredFrom: policy.riskCoveredFrom,
      riskCoveredTo: policy.riskCoveredTo,
      notes: policy.notes,
      freeField1: policy.freeField1,
      freeField2: policy.freeField2,
      freeField3: policy.freeField3,
      freeField4: policy.freeField4,
      freeField5: policy.freeField5,
      maturityDate: policy.maturityDate,
      uinNo: policy.uinNo,
      lastModified: policy.lastModified,
      lastModifiedBy: policy.lastModifiedBy,
      agency: policy.agency,
      company: policy.company,
      product: policy.product,
      premiunDetails: policy.premiunDetails,
      vehicleClass: policy.vehicleClass,
      bankDetails: policy.bankDetails,
      policyUsers: policy.policyUsers,
    });

    this.agenciesCollection = this.agencyService.addAgencyToCollectionIfMissing(this.agenciesCollection, policy.agency);
    this.companiesCollection = this.companyService.addCompanyToCollectionIfMissing(this.companiesCollection, policy.company);
    this.productsCollection = this.productService.addProductToCollectionIfMissing(this.productsCollection, policy.product);
    this.premiunDetailsCollection = this.premiunDetailsService.addPremiunDetailsToCollectionIfMissing(
      this.premiunDetailsCollection,
      policy.premiunDetails
    );
    this.vehicleClassesCollection = this.vehicleClassService.addVehicleClassToCollectionIfMissing(
      this.vehicleClassesCollection,
      policy.vehicleClass
    );
    this.bankDetailsCollection = this.bankDetailsService.addBankDetailsToCollectionIfMissing(
      this.bankDetailsCollection,
      policy.bankDetails
    );
    this.policyUsersSharedCollection = this.policyUsersService.addPolicyUsersToCollectionIfMissing(
      this.policyUsersSharedCollection,
      policy.policyUsers
    );
  }

  protected loadRelationshipsOptions(): void {
    this.agencyService
      .query({ 'policyId.specified': 'false' })
      .pipe(map((res: HttpResponse<IAgency[]>) => res.body ?? []))
      .pipe(map((agencies: IAgency[]) => this.agencyService.addAgencyToCollectionIfMissing(agencies, this.editForm.get('agency')!.value)))
      .subscribe((agencies: IAgency[]) => (this.agenciesCollection = agencies));

    this.companyService
      .query({ 'policyId.specified': 'false' })
      .pipe(map((res: HttpResponse<ICompany[]>) => res.body ?? []))
      .pipe(
        map((companies: ICompany[]) => this.companyService.addCompanyToCollectionIfMissing(companies, this.editForm.get('company')!.value))
      )
      .subscribe((companies: ICompany[]) => (this.companiesCollection = companies));

    this.productService
      .query({ 'policyId.specified': 'false' })
      .pipe(map((res: HttpResponse<IProduct[]>) => res.body ?? []))
      .pipe(
        map((products: IProduct[]) => this.productService.addProductToCollectionIfMissing(products, this.editForm.get('product')!.value))
      )
      .subscribe((products: IProduct[]) => (this.productsCollection = products));

    this.premiunDetailsService
      .query({ 'policyId.specified': 'false' })
      .pipe(map((res: HttpResponse<IPremiunDetails[]>) => res.body ?? []))
      .pipe(
        map((premiunDetails: IPremiunDetails[]) =>
          this.premiunDetailsService.addPremiunDetailsToCollectionIfMissing(premiunDetails, this.editForm.get('premiunDetails')!.value)
        )
      )
      .subscribe((premiunDetails: IPremiunDetails[]) => (this.premiunDetailsCollection = premiunDetails));

    this.vehicleClassService
      .query({ 'policyId.specified': 'false' })
      .pipe(map((res: HttpResponse<IVehicleClass[]>) => res.body ?? []))
      .pipe(
        map((vehicleClasses: IVehicleClass[]) =>
          this.vehicleClassService.addVehicleClassToCollectionIfMissing(vehicleClasses, this.editForm.get('vehicleClass')!.value)
        )
      )
      .subscribe((vehicleClasses: IVehicleClass[]) => (this.vehicleClassesCollection = vehicleClasses));

    this.bankDetailsService
      .query({ 'policyId.specified': 'false' })
      .pipe(map((res: HttpResponse<IBankDetails[]>) => res.body ?? []))
      .pipe(
        map((bankDetails: IBankDetails[]) =>
          this.bankDetailsService.addBankDetailsToCollectionIfMissing(bankDetails, this.editForm.get('bankDetails')!.value)
        )
      )
      .subscribe((bankDetails: IBankDetails[]) => (this.bankDetailsCollection = bankDetails));

    this.policyUsersService
      .query()
      .pipe(map((res: HttpResponse<IPolicyUsers[]>) => res.body ?? []))
      .pipe(
        map((policyUsers: IPolicyUsers[]) =>
          this.policyUsersService.addPolicyUsersToCollectionIfMissing(policyUsers, this.editForm.get('policyUsers')!.value)
        )
      )
      .subscribe((policyUsers: IPolicyUsers[]) => (this.policyUsersSharedCollection = policyUsers));
  }

  protected createFromForm(): IPolicy {
    return {
      ...new Policy(),
      id: this.editForm.get(['id'])!.value,
      policyAmount: this.editForm.get(['policyAmount'])!.value,
      policyNumber: this.editForm.get(['policyNumber'])!.value,
      term: this.editForm.get(['term'])!.value,
      ppt: this.editForm.get(['ppt'])!.value,
      commDate: this.editForm.get(['commDate'])!.value,
      proposerName: this.editForm.get(['proposerName'])!.value,
      sumAssuredAmount: this.editForm.get(['sumAssuredAmount'])!.value,
      premiumMode: this.editForm.get(['premiumMode'])!.value,
      basicPremium: this.editForm.get(['basicPremium'])!.value,
      extraPremium: this.editForm.get(['extraPremium'])!.value,
      gst: this.editForm.get(['gst'])!.value,
      status: this.editForm.get(['status'])!.value,
      totalPremiun: this.editForm.get(['totalPremiun'])!.value,
      gstFirstYear: this.editForm.get(['gstFirstYear'])!.value,
      netPremium: this.editForm.get(['netPremium'])!.value,
      taxBeneficiary: this.editForm.get(['taxBeneficiary'])!.value,
      policyReceived: this.editForm.get(['policyReceived'])!.value,
      previousPolicy: this.editForm.get(['previousPolicy'])!.value,
      policyStartDate: this.editForm.get(['policyStartDate'])!.value,
      policyEndDate: this.editForm.get(['policyEndDate'])!.value,
      period: this.editForm.get(['period'])!.value,
      claimDone: this.editForm.get(['claimDone'])!.value,
      freeHeathCheckup: this.editForm.get(['freeHeathCheckup'])!.value,
      zone: this.editForm.get(['zone'])!.value,
      noOfYear: this.editForm.get(['noOfYear'])!.value,
      floaterSum: this.editForm.get(['floaterSum'])!.value,
      tpa: this.editForm.get(['tpa'])!.value,
      paymentDate: this.editForm.get(['paymentDate'])!.value,
      policyType: this.editForm.get(['policyType'])!.value,
      paToOwner: this.editForm.get(['paToOwner'])!.value,
      paToOther: this.editForm.get(['paToOther'])!.value,
      loading: this.editForm.get(['loading'])!.value,
      riskCoveredFrom: this.editForm.get(['riskCoveredFrom'])!.value,
      riskCoveredTo: this.editForm.get(['riskCoveredTo'])!.value,
      notes: this.editForm.get(['notes'])!.value,
      freeField1: this.editForm.get(['freeField1'])!.value,
      freeField2: this.editForm.get(['freeField2'])!.value,
      freeField3: this.editForm.get(['freeField3'])!.value,
      freeField4: this.editForm.get(['freeField4'])!.value,
      freeField5: this.editForm.get(['freeField5'])!.value,
      maturityDate: this.editForm.get(['maturityDate'])!.value,
      uinNo: this.editForm.get(['uinNo'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      agency: this.editForm.get(['agency'])!.value,
      company: this.editForm.get(['company'])!.value,
      product: this.editForm.get(['product'])!.value,
      premiunDetails: this.editForm.get(['premiunDetails'])!.value,
      vehicleClass: this.editForm.get(['vehicleClass'])!.value,
      bankDetails: this.editForm.get(['bankDetails'])!.value,
      policyUsers: this.editForm.get(['policyUsers'])!.value,
    };
  }
}
