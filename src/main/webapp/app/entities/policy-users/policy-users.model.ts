import { IUsersType } from 'app/entities/users-type/users-type.model';
import { IPolicy } from 'app/entities/policy/policy.model';
import { IAddress } from 'app/entities/address/address.model';
import { StatusInd } from 'app/entities/enumerations/status-ind.model';

export interface IPolicyUsers {
  id?: number;
  groupCode?: string | null;
  groupHeadName?: string | null;
  firstName?: string | null;
  lastName?: string | null;
  birthDate?: string;
  marriageDate?: string;
  userTypeId?: number | null;
  username?: string;
  password?: string;
  email?: string | null;
  imageUrl?: string | null;
  status?: StatusInd | null;
  activated?: boolean;
  licenceExpiryDate?: string | null;
  mobileNo?: string | null;
  aadharCardNuber?: string | null;
  pancardNumber?: string | null;
  oneTimePassword?: string | null;
  otpExpiryTime?: string | null;
  lastModified?: string;
  lastModifiedBy?: string;
  usersType?: IUsersType | null;
  policies?: IPolicy[] | null;
  addresses?: IAddress[] | null;
}

export class PolicyUsers implements IPolicyUsers {
  constructor(
    public id?: number,
    public groupCode?: string | null,
    public groupHeadName?: string | null,
    public firstName?: string | null,
    public lastName?: string | null,
    public birthDate?: string,
    public marriageDate?: string,
    public userTypeId?: number | null,
    public username?: string,
    public password?: string,
    public email?: string | null,
    public imageUrl?: string | null,
    public status?: StatusInd | null,
    public activated?: boolean,
    public licenceExpiryDate?: string | null,
    public mobileNo?: string | null,
    public aadharCardNuber?: string | null,
    public pancardNumber?: string | null,
    public oneTimePassword?: string | null,
    public otpExpiryTime?: string | null,
    public lastModified?: string,
    public lastModifiedBy?: string,
    public usersType?: IUsersType | null,
    public policies?: IPolicy[] | null,
    public addresses?: IAddress[] | null
  ) {
    this.activated = this.activated ?? false;
  }
}

export function getPolicyUsersIdentifier(policyUsers: IPolicyUsers): number | undefined {
  return policyUsers.id;
}
