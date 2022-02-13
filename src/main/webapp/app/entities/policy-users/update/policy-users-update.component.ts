import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IPolicyUsers, PolicyUsers } from '../policy-users.model';
import { PolicyUsersService } from '../service/policy-users.service';
import { IUsersType } from 'app/entities/users-type/users-type.model';
import { UsersTypeService } from 'app/entities/users-type/service/users-type.service';
import { StatusInd } from 'app/entities/enumerations/status-ind.model';

@Component({
  selector: 'jhi-policy-users-update',
  templateUrl: './policy-users-update.component.html',
})
export class PolicyUsersUpdateComponent implements OnInit {
  isSaving = false;
  statusIndValues = Object.keys(StatusInd);

  usersTypesCollection: IUsersType[] = [];

  editForm = this.fb.group({
    id: [],
    groupCode: [],
    groupHeadName: [],
    firstName: [],
    lastName: [],
    birthDate: [null, [Validators.required]],
    marriageDate: [null, [Validators.required]],
    userTypeId: [],
    username: [null, [Validators.required]],
    password: [null, [Validators.required]],
    email: [null, []],
    imageUrl: [],
    status: [],
    activated: [null, [Validators.required]],
    licenceExpiryDate: [],
    mobileNo: [],
    aadharCardNuber: [],
    pancardNumber: [],
    oneTimePassword: [],
    otpExpiryTime: [],
    lastModified: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
    usersType: [],
  });

  constructor(
    protected policyUsersService: PolicyUsersService,
    protected usersTypeService: UsersTypeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ policyUsers }) => {
      this.updateForm(policyUsers);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const policyUsers = this.createFromForm();
    if (policyUsers.id !== undefined) {
      this.subscribeToSaveResponse(this.policyUsersService.update(policyUsers));
    } else {
      this.subscribeToSaveResponse(this.policyUsersService.create(policyUsers));
    }
  }

  trackUsersTypeById(index: number, item: IUsersType): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPolicyUsers>>): void {
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

  protected updateForm(policyUsers: IPolicyUsers): void {
    this.editForm.patchValue({
      id: policyUsers.id,
      groupCode: policyUsers.groupCode,
      groupHeadName: policyUsers.groupHeadName,
      firstName: policyUsers.firstName,
      lastName: policyUsers.lastName,
      birthDate: policyUsers.birthDate,
      marriageDate: policyUsers.marriageDate,
      userTypeId: policyUsers.userTypeId,
      username: policyUsers.username,
      password: policyUsers.password,
      email: policyUsers.email,
      imageUrl: policyUsers.imageUrl,
      status: policyUsers.status,
      activated: policyUsers.activated,
      licenceExpiryDate: policyUsers.licenceExpiryDate,
      mobileNo: policyUsers.mobileNo,
      aadharCardNuber: policyUsers.aadharCardNuber,
      pancardNumber: policyUsers.pancardNumber,
      oneTimePassword: policyUsers.oneTimePassword,
      otpExpiryTime: policyUsers.otpExpiryTime,
      lastModified: policyUsers.lastModified,
      lastModifiedBy: policyUsers.lastModifiedBy,
      usersType: policyUsers.usersType,
    });

    this.usersTypesCollection = this.usersTypeService.addUsersTypeToCollectionIfMissing(this.usersTypesCollection, policyUsers.usersType);
  }

  protected loadRelationshipsOptions(): void {
    this.usersTypeService
      .query({ 'policyUsersId.specified': 'false' })
      .pipe(map((res: HttpResponse<IUsersType[]>) => res.body ?? []))
      .pipe(
        map((usersTypes: IUsersType[]) =>
          this.usersTypeService.addUsersTypeToCollectionIfMissing(usersTypes, this.editForm.get('usersType')!.value)
        )
      )
      .subscribe((usersTypes: IUsersType[]) => (this.usersTypesCollection = usersTypes));
  }

  protected createFromForm(): IPolicyUsers {
    return {
      ...new PolicyUsers(),
      id: this.editForm.get(['id'])!.value,
      groupCode: this.editForm.get(['groupCode'])!.value,
      groupHeadName: this.editForm.get(['groupHeadName'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      birthDate: this.editForm.get(['birthDate'])!.value,
      marriageDate: this.editForm.get(['marriageDate'])!.value,
      userTypeId: this.editForm.get(['userTypeId'])!.value,
      username: this.editForm.get(['username'])!.value,
      password: this.editForm.get(['password'])!.value,
      email: this.editForm.get(['email'])!.value,
      imageUrl: this.editForm.get(['imageUrl'])!.value,
      status: this.editForm.get(['status'])!.value,
      activated: this.editForm.get(['activated'])!.value,
      licenceExpiryDate: this.editForm.get(['licenceExpiryDate'])!.value,
      mobileNo: this.editForm.get(['mobileNo'])!.value,
      aadharCardNuber: this.editForm.get(['aadharCardNuber'])!.value,
      pancardNumber: this.editForm.get(['pancardNumber'])!.value,
      oneTimePassword: this.editForm.get(['oneTimePassword'])!.value,
      otpExpiryTime: this.editForm.get(['otpExpiryTime'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      usersType: this.editForm.get(['usersType'])!.value,
    };
  }
}
