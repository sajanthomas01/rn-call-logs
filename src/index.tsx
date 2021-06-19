import { NativeModules } from 'react-native';

interface LogArrayData {
  name?: string;
  number: string;
  date?: string;
  duration: string;
  country?: string;
  type: string;
}

interface commonFilter {
  fromEpoch: number;
  toEpoch: number;
  limit?: number;
  skip?: number;
}

interface commonFilterWithNumber {
  fromEpoch: number;
  toEpoch: number;
  phoneNumber: string;
  skip?: number;
  limit?: number;
  type?:
    | 'INCOMING'
    | 'OUTGOING'
    | 'MISSED'
    | 'VOICEMAIL'
    | 'REJECTED'
    | 'BLOCKED'
    | 'EXTERNAL'
    | 'ALL';
}

type RnCallLogsType = {
  multiply(a: number, b: number): Promise<number>;
  getAllLogs(filter: commonFilter): Promise<[LogArrayData] | string>;
  getOutgoingLogs(filter: commonFilter): Promise<[LogArrayData] | string>;
  getIncomingLogs(filter: commonFilter): Promise<[LogArrayData] | string>;
  getMissedLogs(filter: commonFilter): Promise<[LogArrayData] | string>;
  getRejectedLogs(filter: commonFilter): Promise<[LogArrayData] | string>;
  getBlockedLogs(filter: commonFilter): Promise<[LogArrayData] | string>;
  getExternallyAnsweredLogs(
    filter: commonFilter
  ): Promise<[LogArrayData] | string>;
  getByNumber(filter: commonFilterWithNumber): Promise<[LogArrayData] | string>;
  getNotConnectedLogs(filter: commonFilter): Promise<[LogArrayData] | string>;
};

const { RnCallLogs } = NativeModules;
const RNCallLog = RnCallLogs as RnCallLogsType;
const {
  getAllLogs,
  multiply,
  getOutgoingLogs,
  getIncomingLogs,
  getMissedLogs,
  getRejectedLogs,
  getBlockedLogs,
  getExternallyAnsweredLogs,
  getByNumber,
  getNotConnectedLogs,
} = RNCallLog;
export {
  getAllLogs,
  multiply,
  getOutgoingLogs,
  getIncomingLogs,
  getMissedLogs,
  getRejectedLogs,
  getBlockedLogs,
  getExternallyAnsweredLogs,
  getByNumber,
  getNotConnectedLogs,
};
