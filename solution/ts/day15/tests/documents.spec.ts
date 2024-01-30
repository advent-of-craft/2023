import {beforeAll, describe, test} from "@jest/globals";
import {configure} from "approvals";
import {JestReporter} from "approvals/lib/Providers/Jest/JestReporter";
import {verifyAllCombinations2} from "approvals/lib/Providers/Jest/CombinationApprovals";
import {RecordType} from "../src/recordType";
import {DocumentTemplates} from "../src/documentTemplateType";

describe("documents", () => {
    beforeAll(() => {
        configure({
            reporters: [new JestReporter()],
        });
    });

    test("golden master for refactoring", () => {
        verifyAllCombinations2(
            (dtt, rt) => DocumentTemplates.fromDocumentTypeAndRecordType(dtt, rt).documentType,
            documentTemplates,
            recordTypes
        )
    });
});

const recordTypes: string[] = Object.keys(RecordType).filter((item) => {
    return isNaN(Number(item));
});

const documentTemplates: string[] = Array.from(DocumentTemplates.values).map(pair => pair[0]);