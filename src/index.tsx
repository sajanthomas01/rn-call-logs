import { NativeModules } from 'react-native';

interface LogArrayData {
  number: string;
  date: string;
  duration: string;
  country: string;
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
  getAllLogs(filter: commonFilter): Promise<[LogArrayData] | []>;
  getOutgoingLogs(filter: commonFilter): Promise<[LogArrayData] | []>;
  getIncomingLogs(filter: commonFilter): Promise<[LogArrayData] | []>;
  getMissedLogs(filter: commonFilter): Promise<[LogArrayData] | []>;
  getRejectedLogs(filter: commonFilter): Promise<[LogArrayData] | []>;
  getBlockedLogs(filter: commonFilter): Promise<[LogArrayData] | []>;
  getExternallyAnsweredLogs(filter: commonFilter): Promise<[LogArrayData] | []>;
  getByNumber(filter: commonFilterWithNumber): Promise<[LogArrayData] | []>;
  getNotConnectedLogs(filter: commonFilter): Promise<[LogArrayData] | []>;
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
