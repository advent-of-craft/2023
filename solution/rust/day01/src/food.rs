use chrono::{DateTime, Utc};

pub struct Food {
    pub expiration_date: DateTime<Utc>,
    pub approved_for_consumption: bool,
    pub inspector_id: Option<uuid::Uuid>,
}

impl Food {
    pub fn is_edible(&self, now: &DateTime<Utc>) -> bool {
        self.is_fresh(now) &&
            self.can_be_consumed() &&
            self.has_been_inspected()
    }

    fn is_fresh(&self, now: &DateTime<Utc>) -> bool {
        self.expiration_date > *now
    }

    fn has_been_inspected(&self) -> bool {
        self.inspector_id.is_some()
    }

    fn can_be_consumed(&self) -> bool {
        self.approved_for_consumption
    }

}