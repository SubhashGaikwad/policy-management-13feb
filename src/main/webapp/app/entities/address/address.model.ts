import { IPolicyUsers } from 'app/entities/policy-users/policy-users.model';
import { ICompany } from 'app/entities/company/company.model';

export interface IAddress {
  id?: number;
  area?: string | null;
  landmark?: string | null;
  taluka?: string | null;
  district?: string | null;
  state?: string | null;
  pincode?: number | null;
  lastModified?: string;
  lastModifiedBy?: string;
  policyUsers?: IPolicyUsers | null;
  company?: ICompany | null;
}

export class Address implements IAddress {
  constructor(
    public id?: number,
    public area?: string | null,
    public landmark?: string | null,
    public taluka?: string | null,
    public district?: string | null,
    public state?: string | null,
    public pincode?: number | null,
    public lastModified?: string,
    public lastModifiedBy?: string,
    public policyUsers?: IPolicyUsers | null,
    public company?: ICompany | null
  ) {}
}

export function getAddressIdentifier(address: IAddress): number | undefined {
  return address.id;
}
