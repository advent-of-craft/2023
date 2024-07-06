use std::ops::{Sub, Add, Deref};
use chrono::{DateTime, Duration, Utc};
use once_cell::sync::Lazy;
use uuid::Uuid;

use day01::food::Food;


#[test]
fn not_edible_if_not_fresh() {
    let arguments = vec![
        (true, Some(&INSPECTOR), &NOT_FRESH_DATE),
        (false, Some(&INSPECTOR), &FRESH_DATE),
        (true, None, &FRESH_DATE),
        (false, None, &NOT_FRESH_DATE),
        (false, None, &FRESH_DATE),
    ];

    let res = arguments
        .into_iter()
        .map(|(approved, inspertor_id, date_expiration)| {
            Food {
                expiration_date: *(date_expiration.deref()),
                approved_for_consumption: approved,
                inspector_id: inspertor_id.map(|x| *(x.deref())),
            }
        })
        .map(|food| food.is_edible(NOW.deref()))
        .any(|x| !x);

    assert!(res)
}

#[test]
fn edible_food() {

    let food = Food {
        expiration_date: *(EXPIRATION_DATE.deref()),
        approved_for_consumption: true,
        inspector_id: Some(*INSPECTOR.deref()),
    };

    let is_edible = food.is_edible(FRESH_DATE.deref());

    assert!(is_edible)
}


static NOW: Lazy<DateTime<Utc>> = Lazy::new(|| {
    Utc::now()
});

static INSPECTOR: Lazy<Uuid> = Lazy::new(|| {
    Uuid::new_v4()
});

static EXPIRATION_DATE: Lazy<DateTime<Utc>> = Lazy::new(|| {
    *NOW.deref()
});
static NOT_FRESH_DATE: Lazy<DateTime<Utc>> = Lazy::new(|| {
    EXPIRATION_DATE.add(Duration::days(7))
});

static FRESH_DATE: Lazy<DateTime<Utc>> = Lazy::new(|| {
    EXPIRATION_DATE.sub(Duration::days(7))
});