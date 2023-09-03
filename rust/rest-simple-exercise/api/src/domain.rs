use serde::{Deserialize, Serialize};
use utoipa::ToSchema;

#[derive(Serialize, Deserialize, Clone, ToSchema)]
pub(super) struct ErrorResponse {
    pub(crate) message: String,
    pub(crate) error: String,
}

#[derive(Serialize, Deserialize, Clone, ToSchema)]
pub(super) struct MessageResponse {
    pub(crate) message: String,
}
