use chrono::{DateTime, Utc};

pub struct Food {
    pub expiration_date: DateTime<Utc>,
    pub approved_for_consumption: bool,
    pub inspector_id: Option<uuid::Uuid>,
}

impl Food {
    pub fn is_edible(&self, now: &DateTime<Utc>) -> bool {
        self.expiration_date > *now &&
            self.approved_for_consumption &&
            self.inspector_id.is_some()
    }
}