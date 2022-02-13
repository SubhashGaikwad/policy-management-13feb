import { ICompanyType } from 'app/entities/company-type/company-type.model';
import { IProduct } from 'app/entities/product/product.model';
import { IAddress } from 'app/entities/address/address.model';
import { IPolicy } from 'app/entities/policy/policy.model';

export interface ICompany {
  id?: number;
  name?: string | null;
  address?: string | null;
  branch?: string | null;
  brnachCode?: string | null;
  email?: string | null;
  imageUrl?: string | null;
  contactNo?: string | null;
  lastModified?: string;
  lastModifiedBy?: string;
  companyType?: ICompanyType | null;
  products?: IProduct[] | null;
  addresses?: IAddress[] | null;
  policy?: IPolicy | null;
}

export class Company implements ICompany {
  constructor(
    public id?: number,
    public name?: string | null,
    public address?: string | null,
    public branch?: string | null,
    public brnachCode?: string | null,
    public email?: string | null,
    public imageUrl?: string | null,
    public contactNo?: string | null,
    public lastModified?: string,
    public lastModifiedBy?: string,
    public companyType?: ICompanyType | null,
    public products?: IProduct[] | null,
    public addresses?: IAddress[] | null,
    public policy?: IPolicy | null
  ) {}
}

export function getCompanyIdentifier(company: ICompany): number | undefined {
  return company.id;
}
