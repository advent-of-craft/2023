namespace Day21
{
    public sealed class AddRecordUseCase(string directoryName, AuditManager auditManager, Persister persister)
    {
        public void Handle(string visitorName, DateTime timeOfVisit)
        {
            var files = persister.ReadDirectory(directoryName);

            persister.ApplyUpdate(directoryName,
                auditManager.AddRecord(files, visitorName, timeOfVisit)
            );
        }
    }
}