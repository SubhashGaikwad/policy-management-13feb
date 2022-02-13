import { ICompany } from 'app/entities/company/company.model';

export interface ICompanyType {
  id?: number;
  name?: string | null;
  lastModified?: string;
  lastModifiedBy?: string;
  company?: ICompany | null;
}

export class CompanyType implements ICompanyType {
  constructor(
    public id?: number,
    public name?: string | null,
    public lastModified?: string,
    public lastModifiedBy?: string,
    public company?: ICompany | null
  ) {}
}

export function getCompanyTypeIdentifier(companyType: ICompanyType): number | undefined {
  return companyType.id;
}
